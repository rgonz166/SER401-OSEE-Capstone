import { Routes } from '@angular/router';

const routes: Routes = [
	{
		path: '',
		loadComponent: () =>
			import('./kanban.component').then((m) => m.KanbanComponent),
	},
	{
		path: 'initsearch',
		loadComponent: () =>
			import(
				'./components/initial-search-criteria/init.component'
			).then((m) => m.InitSearchComponent),
	},
];

export default routes;