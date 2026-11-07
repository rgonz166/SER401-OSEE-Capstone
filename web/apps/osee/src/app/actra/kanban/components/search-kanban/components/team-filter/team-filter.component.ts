import { ChangeDetectionStrategy, Component, computed, inject, signal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { MatButton } from '@angular/material/button';
import { MatOption } from '@angular/material/core';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatSelect, MatSelectChange } from '@angular/material/select';
import { catchError, map, of } from 'rxjs';
import { ActraWorldHttpService } from '../../../../../services/actra-world-http.service';

@Component({
	selector: 'osee-team-filter',
	imports: [MatFormField, MatLabel, MatSelect, MatOption, MatButton],
	templateUrl: './team-filter.component.html',
	changeDetection: ChangeDetectionStrategy.OnPush,
})
export class TeamFilterComponent {
	private readonly loadedData = toSignal(
		inject(ActraWorldHttpService).getWorldDataMy().pipe(
			map((data) => ({
				teams: [...new Set(
					data.rows.map((row) => row['Team']?.trim() ?? '').filter(Boolean)
				)].sort((a, b) => a.localeCompare(b)),
				loading: false,
				error: '',
			})),
			catchError(() => of({
				teams: [] as string[],
				loading: false,
				error: 'Unable to load teams. Reload the page to try again.',
			}))
		),
		{ initialValue: { teams: [] as string[], loading: true, error: '' } }
	);
	readonly teams = computed(() => this.loadedData().teams);
	protected readonly loading = computed(() => this.loadedData().loading);
	protected readonly error = computed(() => this.loadedData().error);

	readonly selected = signal<string[]>([]);

	setTeam(event: MatSelectChange): void {
		this.selected.set(event.value);
	}

	clearTeam(): void {
		this.selected.set([]);
	}
}
