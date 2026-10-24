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
});
