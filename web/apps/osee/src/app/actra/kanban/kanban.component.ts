import { ChangeDetectionStrategy, Component } from '@angular/core';
import { SearchKanbanComponent } from './components/search-kanban/search-kanban.component';
import { BoardComponent } from './components/board/board.component';
import { InitialSearchCriteriaComponent } from './components/initial-search-criteria/initial-search-criteria';

@Component({
	selector: 'osee-kanban',
	imports: [SearchKanbanComponent, BoardComponent, InitialSearchCriteriaComponent],
	templateUrl: './kanban.component.html',
	changeDetection: ChangeDetectionStrategy.OnPush,
})
export class KanbanComponent {}

export default KanbanComponent;
