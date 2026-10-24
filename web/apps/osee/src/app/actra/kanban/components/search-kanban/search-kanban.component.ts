import { ChangeDetectionStrategy, Component } from '@angular/core';
import { AssigneeSearchComponent } from './components/assignee-search/assignee-search.component';
import { SearchWorkflowComponent } from './components/search-workflow/search-workflow.component'

import { TeamFilterComponent } from './components/team-filter/team-filter.component';

@Component({
	selector: 'osee-search-kanban',
	imports: [AssigneeSearchComponent, TeamFilterComponent, SearchWorkflowComponent],

	templateUrl: './search-kanban.component.html',
	changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SearchKanbanComponent {}