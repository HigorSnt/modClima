import React from 'react';
import { Tabs, TabList, Tab, TabPanel } from 'react-tabs';

import FarmsList from './Farms';
import FieldsList from './Fields';
import HarvestsList from './Harvests';
import MillsList from './Mills';

import 'react-tabs/style/react-tabs.css';
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
					<MillsList />
				</TabPanel>

				<TabPanel>
					<HarvestsList />
				</TabPanel>

				<TabPanel>
					<FarmsList />
				</TabPanel>

				<TabPanel>
					<FieldsList />
				</TabPanel>
			</Tabs>
		</div>
	);
};

export default List;
