import { ChangeDetectionStrategy, Component } from '@angular/core';
import { SearchKanbanComponent } from './components/search-kanban/search-kanban.component';
import { KanbanBoard } from './models/kanban-board';

@Component({
	selector: 'osee-kanban',
	imports: [SearchKanbanComponent],
	templateUrl: './kanban.component.html',
	changeDetection: ChangeDetectionStrategy.OnPush,
})
export class KanbanComponent {
	readonly board: KanbanBoard = {
		columns: [
			{
				id: 'open',
				title: 'Open',
				cards: [
					{
						id: 'card-1',
						title: 'Set up the board',
						columnId: 'open',
						assignees: ['Ali Shaban'],
						syncStatus: 'synced',
					},
				],
			},
			{
				id: 'analyze',
				title: 'Analyze',
				cards: [
					{
						id: 'card-2',
						title: 'Review the column names',
						columnId: 'analyze',
						assignees: ['Kai Pelton'],
						syncStatus: 'pending',
					},
				],
			},
			{ id: 'implement', title: 'Implement', cards: [] },
			{
				id: 'review',
				title: 'Review',
				cards: [
					{
						id: 'card-3',
						title: 'Review the board layout',
						columnId: 'review',
						assignees: ['Liezel Vallar'],
						syncStatus: 'error',
					},
				],
			},
			{ id: 'completed', title: 'Completed', cards: [] },
			{ id: 'cancelled', title: 'Cancelled', cards: [] },
		],
	};
}

export default KanbanComponent;
