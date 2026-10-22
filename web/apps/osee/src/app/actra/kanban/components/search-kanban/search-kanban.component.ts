import { ChangeDetectionStrategy, Component } from '@angular/core';
import { AssigneeSearchComponent } from './components/assignee-search/assignee-search.component';
import { TeamFilterComponent } from '../team-filter/team-filter.component';

@Component({
	selector: 'osee-search-kanban',
	imports: [AssigneeSearchComponent, TeamFilterComponent],
	templateUrl: './search-kanban.component.html',
	changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SearchKanbanComponent {}