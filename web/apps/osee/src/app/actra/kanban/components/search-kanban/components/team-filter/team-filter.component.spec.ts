
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

	it('should show the selected people', () => {
		component.setTeam({ value: ['Mina Gamble'] } as MatSelectChange);
		fixture.detectChanges();

		const summary = fixture.nativeElement.querySelector(
			'#team-filter-summary'
		);
		expect(summary.textContent).toContain('Mina Gamble');
	});

	it('should clear the selected team', () => {
		component.setTeam(
			{ value: ['Ali Shaban', 'Kai Pelton'] } as MatSelectChange
		);
		fixture.detectChanges();

		fixture.nativeElement.querySelector('#clear-team-filter').click();
		fixture.detectChanges();

		expect(component.selected()).toEqual([]);
		expect(
			fixture.nativeElement.querySelector('#clear-team-filter')
		).toBeNull();
		expect(
			fixture.nativeElement.querySelector('#team-filter-summary')
		).toBeNull();
	});
});
