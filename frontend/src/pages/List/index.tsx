import React from 'react';
import { Tabs, TabList, Tab, TabPanel } from 'react-tabs';

import 'react-tabs/style/react-tabs.css';
import Mills from './Mills';
import './styles.css';

const List: React.FC = () => {
	return (
		<div id="list-container">
			<Tabs>
				<TabList>
					<Tab>Mills</Tab>
					<Tab>Harvests</Tab>
					<Tab>Farms</Tab>
					<Tab>Fields</Tab>
				</TabList>
				<TabPanel>
					<Mills />
				</TabPanel>
				<TabPanel><p>kvndjkcnd</p></TabPanel>
				<TabPanel><p>cklkdackld</p></TabPanel>
				<TabPanel><p>cdncdjkliio</p></TabPanel>
			</Tabs>
		</div>
	);
};

export default List;
