import { ChangeDetectionStrategy, Component, computed, inject, model } from '@angular/core';
import { MatButton } from '@angular/material/button';
import { MatOption } from '@angular/material/core';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatSelect } from '@angular/material/select';
import { toSignal } from '@angular/core/rxjs-interop';
import { catchError, map, of } from 'rxjs';
import { ActionService } from '@osee/configuration-management/services';

type FilterOption = { id: string; name: string };

@Component({
	selector: 'osee-assignee-search',
	imports: [MatFormField, MatLabel, MatSelect, MatOption, MatButton],
	templateUrl: './assignee-search.component.html',
	changeDetection: ChangeDetectionStrategy.OnPush,
})
export class AssigneeSearchComponent {
	private readonly loadedData = toSignal(
		inject(ActionService).users.pipe(
			map((data) => ({
				options: [...data].map((user) => ({ id: user.id, name: user.name }))
					.sort((a, b) => a.name.localeCompare(b.name)),
				loading: false,
				error: '',
			})),
			catchError(() => of({
				options: [] as FilterOption[],
				loading: false,
				error: 'Unable to load assignees. Reload the page to try again.',
			}))
		),
		{ initialValue: { options: [] as FilterOption[], loading: true, error: '' } }
	);
	protected readonly options = computed(() => this.loadedData().options);
	protected readonly loading = computed(() => this.loadedData().loading);
	protected readonly error = computed(() => this.loadedData().error);
	readonly selectedAssigneeIds = model<string[]>([]);
}
