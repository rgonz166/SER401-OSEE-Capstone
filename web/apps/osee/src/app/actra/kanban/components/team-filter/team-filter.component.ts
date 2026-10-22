import { ChangeDetectionStrategy, Component, signal } from '@angular/core';
import { MatButton } from '@angular/material/button';
import { MatOption } from '@angular/material/core';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatSelect, MatSelectChange } from '@angular/material/select';

@Component({
	selector: 'osee-team-filter',
	imports: [MatFormField, MatLabel, MatSelect, MatOption, MatButton],
	templateUrl: './team-filter.component.html',
	changeDetection: ChangeDetectionStrategy.OnPush,
})
export class TeamFilterComponent {
	readonly teamMembers = [
		'Ali Shaban',
		'Kai Pelton',
		'Liezel Vallar',
		'Mina Gamble',
		'Raul Gonzalez',
	];

	readonly selected = signal<string[]>([]);

	setTeam(event: MatSelectChange): void {
		this.selected.set(event.value);
	}

	clearTeam(): void {
		this.selected.set([]);
	}
}
