import React from 'react';
import { BrowserRouter, Switch, Route } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';

import Dashboard from './pages/Dashboard';
import FarmDetails from './pages/Farm';
import FieldDetails from './pages/Field';
import HarvestDetails from './pages/Harvest';
import MillDetails from './pages/Mill';
import Register from './pages/Register';

import 'react-toastify/dist/ReactToastify.css';

const Routes: React.FC = () => {
	return (
		<BrowserRouter>
			<Switch>
				<Route path="/" exact component={Dashboard} />
				<Route path="/mills/:id" component={MillDetails} />
				<Route path="/harvests/:id" component={HarvestDetails} />
				<Route path="/farms/:id" component={FarmDetails} />
				<Route path="/fields/:id" component={FieldDetails} />
				<Route path="/register" component={Register} />
			</Switch>
			<ToastContainer limit={1} />
		</BrowserRouter>
	);
};

export default Routes;
