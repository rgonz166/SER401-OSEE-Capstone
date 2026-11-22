import {
	ChangeDetectionStrategy,
	Component,
	computed,
	inject,
	signal,
} from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatOptionModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { toSignal } from '@angular/core/rxjs-interop';
import { catchError, map, of } from 'rxjs';
import { ActionService } from '@osee/configuration-management/services';
import { PRIORITIES } from '@osee/shared/types/configuration-management';

export const WORK_ITEM_TYPES = [
	'TeamWorkflow',
	'Task',
	'Review',
	'Goal',
	'PeerReview',
	'DecisionReview',
	'AgileSprint',
	'AgileBacklog',
] as const;

export type WorkItemType = (typeof WORK_ITEM_TYPES)[number];

export const STATE_TYPES = ['Working', 'Completed', 'Cancelled'] as const;
export type StateType = (typeof STATE_TYPES)[number];

export const HOLD_STATES = ['On_Hold', 'Not_On_Hold'] as const;
export type HoldState = (typeof HOLD_STATES)[number];

export const CHANGE_TYPES = [
	'Problem',
	'Improvement',
	'Refinement',
	'Support',
	'InitialDev',
	'Fix',
] as const;
export type ChangeType = (typeof CHANGE_TYPES)[number];

export const USER_TYPES = [
	'Assignee',
	'Originated',
	'Subscribed',
	'Favorites',
] as const;
export type UserType = (typeof USER_TYPES)[number];

@Component({
	selector: 'osee-kanban-init-search',
	imports: [
		FormsModule,
		MatButtonModule,
		MatFormFieldModule,
		MatInputModule,
		MatOptionModule,
		MatSelectModule,
	],
	templateUrl: './init.component.html',
	changeDetection: ChangeDetectionStrategy.OnPush,
})
export class InitSearchComponent {
	private readonly actionService = inject(ActionService);

	protected readonly workItemTypes = WORK_ITEM_TYPES;
	protected readonly stateTypes = STATE_TYPES;
	protected readonly holdStates = HOLD_STATES;
	protected readonly changeTypes = CHANGE_TYPES;
	protected readonly userTypes = USER_TYPES;
	protected readonly priorities = Object.entries(PRIORITIES).map(
		([label, value]) => ({ label, value })
	);

	private readonly usersData = toSignal(
		this.actionService.users.pipe(
			map((users) => ({
				options: [...users]
					.map((u) => ({ id: u.userId, name: u.name }))
					.sort((a, b) => a.name.localeCompare(b.name)),
				loading: false,
				error: '',
			})),
			catchError(() =>
				of({ options: [] as { id: string; name: string }[], loading: false, error: 'Unable to load users.' })
			)
		),
		{ initialValue: { options: [] as { id: string; name: string }[], loading: true, error: '' } }
	);
	protected readonly userOptions = computed(() => this.usersData().options);
	protected readonly usersLoading = computed(() => this.usersData().loading);
	protected readonly usersError = computed(() => this.usersData().error);

	readonly title = signal('');
	readonly selectedWorkItemTypes = signal<WorkItemType[]>([]);
	readonly selectedStateTypes = signal<StateType[]>([]);
	readonly stateName = signal('');
	readonly selectedHoldState = signal<HoldState | ''>('');
	readonly selectedChangeTypes = signal<ChangeType[]>([]);
	readonly selectedPriorities = signal<string[]>([]);
	readonly selectedUserId = signal('');
	readonly selectedUserType = signal<UserType | ''>('');
}

export default InitSearchComponent;
