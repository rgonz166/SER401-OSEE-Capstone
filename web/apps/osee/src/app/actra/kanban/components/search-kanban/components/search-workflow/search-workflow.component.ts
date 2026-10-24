
import {
	ChangeDetectionStrategy,
	Component,
	computed,
	inject,
	signal,
} from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';
import { MatIconButton } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import { MatTooltip } from '@angular/material/tooltip';
import { toSignal } from '@angular/core/rxjs-interop';
import { catchError, of, Subject, switchMap } from 'rxjs';
import { ActionService } from '@osee/configuration-management/services';
import { UiService } from '@osee/shared/services';
import type { teamWorkflowDetails } from '@osee/shared/types/configuration-management';

type SearchResult = {
	workflow: teamWorkflowDetails | null;
	loading: boolean;
	error: string;
};

@Component({
	selector: 'osee-search-workflow',
	imports: [
		FormsModule,
		MatFormField,
		MatLabel,
		MatInput,
		MatIconButton,
		MatIcon,
		MatTooltip,
	],
	templateUrl: './search-workflow.component.html',
	changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SearchWorkflowComponent {
	private readonly actionService = inject(ActionService);
	protected readonly uiService = inject(UiService);

	protected readonly searchId = signal('');

	private readonly _search$ = new Subject<string>();

	private readonly _initialResult: SearchResult = {
		workflow: null,
		loading: false,
		error: '',
	};

	private readonly _result = toSignal(
		this._search$.pipe(
			switchMap((id) =>
				this.actionService.getTeamWorkflowDetails(id).pipe(
					switchMap((wf) =>
						of<SearchResult>({ workflow: wf, loading: false, error: '' })
					),
					catchError(() =>
						of<SearchResult>({
							workflow: null,
							loading: false,
							error: 'Workflow not found.',
						})
					)
				)
			)
		),
		{ initialValue: this._initialResult }
	);

	protected readonly workflow = computed(() => this._result().workflow);
	protected readonly loading = computed(() => this._result().loading);
	protected readonly error = computed(() => this._result().error);
	protected readonly hasResult = computed(() => this.workflow() !== null);

	protected search(): void {
		const id = this.searchId().trim();
		if (!id) return;
		this._search$.next(id);
	}

	protected clearSearch(event: MouseEvent): void {
		event.preventDefault();
		event.stopPropagation();
		this.searchId.set('');
	}
}
