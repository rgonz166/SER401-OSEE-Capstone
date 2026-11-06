import { ChangeDetectionStrategy, Component, computed, viewChild } from '@angular/core';
import { AssigneeSearchComponent } from './components/assignee-search/assignee-search.component';
import { SearchWorkflowComponent } from './components/search-workflow/search-workflow.component'

import { TeamFilterComponent } from './components/team-filter/team-filter.component';

@Component({
	selector: 'osee-search-kanban',
	imports: [AssigneeSearchComponent, TeamFilterComponent, SearchWorkflowComponent],

	templateUrl: './search-kanban.component.html',
	changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SearchKanbanComponent {
	private readonly teamFilter = viewChild(TeamFilterComponent);
	private readonly workflowFilter = viewChild(SearchWorkflowComponent);
	private readonly assigneeFilter = viewChild(AssigneeSearchComponent);

	readonly hasSelection = computed(() =>
		(this.teamFilter()?.selected().length ?? 0) > 0 ||
		(this.workflowFilter()?.selectedWorkflowIds().length ?? 0) > 0 ||
		(this.assigneeFilter()?.selectedAssigneeIds().length ?? 0) > 0
	);
}
