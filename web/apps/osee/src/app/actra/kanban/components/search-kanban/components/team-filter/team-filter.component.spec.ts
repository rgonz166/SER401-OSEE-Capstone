
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MatSelectChange } from '@angular/material/select';
import { TeamFilterComponent } from './team-filter.component';
import { of } from 'rxjs';
import { ActraWorldHttpService } from '../../../../../services/actra-world-http.service';
import { worldDataEmpty } from '../../../../../types/actra-types';

describe('TeamFilterComponent', () => {
	let component: TeamFilterComponent;
	let fixture: ComponentFixture<TeamFilterComponent>;

	beforeEach(async () => {
		await TestBed.configureTestingModule({
			imports: [TeamFilterComponent, NoopAnimationsModule],
			providers: [{
				provide: ActraWorldHttpService,
				useValue: { getWorldDataMy: () => of({
					...worldDataEmpty,
					rows: [
						{ Team: 'SAW Requirements' },
						{ Team: 'SAW Code' },
						{ Team: ' SAW Code ' },
						{ Team: '' },
						{},
					],
				}) },
			}],
		}).compileComponents();

		fixture = TestBed.createComponent(TeamFilterComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});

	it('should keep more than one team selected', () => {
		component.setTeam(
			{ value: ['SAW Code', 'SAW Requirements'] } as MatSelectChange
		);
		expect(component.selected()).toEqual(['SAW Code', 'SAW Requirements']);
	});

	it('should populate unique, sorted teams and ignore blank values', () => {
		expect(component.teams()).toEqual(['SAW Code', 'SAW Requirements']);
	});

	it('should render the team filter as a multi-select', () => {
		const team = fixture.nativeElement.querySelector('#team-filter');
		expect(team).not.toBeNull();
		expect(team.hasAttribute('multiple')).toBe(true);
	});
});
