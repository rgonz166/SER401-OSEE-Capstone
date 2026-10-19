import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

import { SearchKanbanComponent } from './search-kanban.component';

describe('SearchKanbanComponent', () => {
	let component: SearchKanbanComponent;
	let fixture: ComponentFixture<SearchKanbanComponent>;

	beforeEach(async () => {
		await TestBed.configureTestingModule({
			imports: [SearchKanbanComponent, NoopAnimationsModule],
		}).compileComponents();

		fixture = TestBed.createComponent(SearchKanbanComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
		await fixture.whenStable();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});

	it('should show the team filter', () => {
		const filter = fixture.nativeElement.querySelector('#team-filter');
		expect(filter).toBeTruthy();
	});
});
