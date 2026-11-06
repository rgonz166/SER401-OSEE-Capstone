import { ChangeDetectionStrategy, Component, computed, inject, input } from '@angular/core';
import { toObservable, toSignal } from '@angular/core/rxjs-interop';
import { ActionService } from '@osee/configuration-management/services';
import { catchError, defer, forkJoin, map, of, startWith, switchMap } from 'rxjs';
import {
	MatCard,
	MatCardContent,
	MatCardHeader,
	MatCardTitle,
} from '@angular/material/card';

export type KanbanCard = {
	readonly id: string;
	readonly title: string;
	readonly description?: string;
	readonly assignee?: string;
};

export type KanbanColumn = {
	readonly id: string;
	readonly title: string;
	readonly cards: readonly KanbanCard[];
};

@Component({
	selector: 'osee-kanban-board',
	imports: [MatCard, MatCardContent, MatCardHeader, MatCardTitle],
	templateUrl: './board.component.html',
	changeDetection: ChangeDetectionStrategy.OnPush,
})
export class BoardComponent {
	readonly selectedWorkflowIds = input<readonly string[]>([]);
	private readonly actionService = inject(ActionService);
	private readonly stateData = toSignal(
		toObservable(this.selectedWorkflowIds).pipe(
			switchMap((ids) => {
				if (ids.length === 0) {
					return of({ columns: [] as KanbanColumn[], loading: false, error: '' });
				}
				return defer(() => forkJoin(ids.map((id) => {
					if (!/^\d+$/.test(id)) {
						throw new Error('Invalid workflow ID');
					}
					return this.actionService.getWorkDefinition(id as `${number}`);
				}))).pipe(
					map((definitions) => {
						const columns = new Map<string, KanbanColumn>();
						for (const definition of definitions) {
							for (const state of definition.states) {
								if (!columns.has(state.name)) {
									columns.set(state.name, { id: state.name, title: state.name, cards: [] });
								}
							}
						}
						return { columns: [...columns.values()], loading: false, error: '' };
					}),
					catchError(() => of({
						columns: [] as KanbanColumn[],
						loading: false,
						error: 'Unable to load workflow states. Select the workflows again to retry.',
					})),
					startWith({ columns: [] as KanbanColumn[], loading: true, error: '' })
				);
			})
		),
		{ initialValue: { columns: [] as KanbanColumn[], loading: false, error: '' } }
	);
	protected readonly columns = computed(() => this.stateData().columns);
	protected readonly loading = computed(() => this.stateData().loading);
	protected readonly error = computed(() => this.stateData().error);
}
