import React from 'react';
import { FaPlus, FaListUl } from 'react-icons/fa';

import logo from '../../images/logo.png';

import './styles.css';

const Menu: React.FC = () => {
	return (
		<aside>
			<img src={logo} alt="cyan agroanalytics" />
			<div id="menu-options-box">
				<div id="menu-options">
					<div id="option-selected" />
					<div id="option-icon" title="Search">
						<FaListUl color="#0d0d0d" size={40} />
					</div>
				</div>
				<div id="menu-options" title="Register">
					<div id="option-selected" />
					<div id="option-icon">
						<FaPlus color="#0d0d0d" size={40} />
					</div>
				</div>
			</div>
		</aside>
	);
};

export default Menu;
