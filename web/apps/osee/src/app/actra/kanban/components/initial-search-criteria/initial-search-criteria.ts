import {
	ChangeDetectionStrategy,
	Component,
	computed,
	inject,
	model,
} from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButton } from '@angular/material/button';
import { MatOption } from '@angular/material/core';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';
import { MatSelect } from '@angular/material/select';
import { toSignal } from '@angular/core/rxjs-interop';
import { catchError, map, of } from 'rxjs';

type StateType = 'Working' | 'Completed' | 'Cancelled';
type TeamOption = { id: string; name: string };

@Component({
	selector: 'osee-initial-search-criteria',
	imports: [
		FormsModule,
		MatButton,
		MatFormField,
		MatInput,
		MatLabel,
		MatOption,
		MatSelect,
	],
	templateUrl: './initial-search-criteria.html',
	changeDetection: ChangeDetectionStrategy.OnPush,
})
export class InitialSearchCriteriaComponent {
	private readonly loadedTeams = null;
	protected readonly teamOptions = null;
	protected readonly teamsLoading = null;
	protected readonly teamsError = null;

	protected readonly stateTypes: StateType[] = [
		'Working',
		'Completed',
		'Cancelled',
	];

	readonly title = model<string>('');
	readonly selectedTeams = model<string[]>([]);
	readonly selectedStateTypes = model<StateType[]>([]);
}
