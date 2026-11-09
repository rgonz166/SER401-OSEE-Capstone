import { ChangeDetectionStrategy, Component, computed, inject, model } from '@angular/core';
import { MatButton } from '@angular/material/button';
import { MatOption } from '@angular/material/core';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatSelect } from '@angular/material/select';
import { toSignal } from '@angular/core/rxjs-interop';
import { catchError, map, of } from 'rxjs';
import { ActraWorldHttpService } from '../../../../../services/actra-world-http.service';

type FilterOption = { id: string; name: string };

@Component({
	selector: 'osee-search-workflow',
	imports: [MatFormField, MatLabel, MatSelect, MatOption, MatButton],
	templateUrl: './search-workflow.component.html',
	changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SearchWorkflowComponent {
	private readonly loadedData = toSignal(
		inject(ActraWorldHttpService).getWorldDataMy().pipe(
			map((data) => ({
				options: data.rows.filter((row) => !!row['id']).map((row) => ({ id: row['id'], name: row['Name'] || row['ATS Id'] || row['id'] }))
					.sort((a, b) => a.name.localeCompare(b.name)),
				loading: false,
				error: '',
			})),
			catchError(() => of({
				options: [] as FilterOption[],
				loading: false,
				error: 'Unable to load workflow. Reload the page to try again.',
			}))
		),
		{ initialValue: { options: [] as FilterOption[], loading: true, error: '' } }
	);
	protected readonly options = computed(() => this.loadedData().options);
	protected readonly loading = computed(() => this.loadedData().loading);
	protected readonly error = computed(() => this.loadedData().error);
	readonly selectedWorkflowIds = model<string[]>([]);
}
