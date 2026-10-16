
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MatSelectChange } from '@angular/material/select';
import { TeamFilterComponent } from './team-filter.component';

describe('TeamFilterComponent', () => {
	let component: TeamFilterComponent;
	let fixture: ComponentFixture<TeamFilterComponent>;

	beforeEach(async () => {
		await TestBed.configureTestingModule({
			imports: [TeamFilterComponent, NoopAnimationsModule],
		}).compileComponents();

		fixture = TestBed.createComponent(TeamFilterComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});

	it('should keep more than one person selected', () => {
		component.setTeam(
			{ value: ['Ali Shaban', 'Kai Pelton'] } as MatSelectChange
		);
		expect(component.selected()).toEqual(['Ali Shaban', 'Kai Pelton']);
	});

	it('should render the team filter as a multi-select', () => {
		const team = fixture.nativeElement.querySelector('#team-filter');
		expect(team).not.toBeNull();
		expect(team.hasAttribute('multiple')).toBe(true);
	});
});
