import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

import { KanbanComponent } from './kanban.component';

describe('KanbanComponent', () => {
	let component: KanbanComponent;
	let fixture: ComponentFixture<KanbanComponent>;

	beforeEach(async () => {
		await TestBed.configureTestingModule({
			imports: [KanbanComponent, NoopAnimationsModule],
		}).compileComponents();

		fixture = TestBed.createComponent(KanbanComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
		await fixture.whenStable();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});

	it('should show the six board columns', () => {
		const columns = fixture.nativeElement.querySelectorAll('section');
		expect(columns.length).toBe(6);
		expect(fixture.nativeElement.textContent).toContain('Open');
		expect(fixture.nativeElement.textContent).toContain('Completed');
	});

	it('should show a card in the review column', () => {
		const review = fixture.nativeElement.querySelector('#column-review');
		expect(review.textContent).toContain('Review the board layout');
		expect(review.textContent).toContain('error');
	});
});
