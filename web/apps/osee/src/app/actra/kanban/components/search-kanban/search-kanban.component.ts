import { ChangeDetectionStrategy, Component } from '@angular/core';
import { AssigneeSearchComponent } from './components/assignee-search/assignee-search.component';
import { SearchWorkflowComponent } from './components/search-workflow/search-workflow.component'
@Component({
	selector: 'osee-search-kanban',
	imports: [AssigneeSearchComponent, SearchWorkflowComponent],
	templateUrl: './search-kanban.component.html',
	changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SearchKanbanComponent {}
