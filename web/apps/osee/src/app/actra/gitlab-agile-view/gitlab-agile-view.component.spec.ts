import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GitlabAgileViewComponent } from './gitlab-agile-view.component';

describe('GitlabAgileViewComponent', () => {
  let component: GitlabAgileViewComponent;
  let fixture: ComponentFixture<GitlabAgileViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GitlabAgileViewComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GitlabAgileViewComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
