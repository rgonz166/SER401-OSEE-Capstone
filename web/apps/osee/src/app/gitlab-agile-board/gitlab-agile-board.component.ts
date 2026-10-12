
import { Component } from '@angular/core';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatOption, MatSelect } from '@angular/material/select';

@Component({
    selector: 'osee-gitlab-agile-board',
    imports: [MatLabel, MatFormField, MatSelect, MatOption],
    templateUrl: './gitlab-agile-board.component.html',
})
class GitlabAgileBoardComponent {
  toppingList: string[] = ['Extra cheese', 'Mushroom', 'Onion', 'Pepperoni', 'Sausage', 'Tomato'];
}

export default GitlabAgileBoardComponent;
