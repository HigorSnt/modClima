import React from 'react';
import List from '../List';
import Menu from '../../components/Menu';

import './styles.css';

const Dashboard: React.FC = () => {
	return (
		<div id="dashboard-box">
			<Menu />
			<List />
		</div>
	);
};

export default Dashboard;
