import React from 'react';
import { Link } from 'react-router-dom';
import { FaPlus, FaListUl } from 'react-icons/fa';

import logo from '../../images/logo.png';

import './styles.css';

const Menu: React.FC = () => {
	return (
		<aside>
			<Link to="/">
				<img src={logo} alt="cyan agroanalytics" />
			</Link>
			<div id="menu-options-box">
				<Link to="/" title="Search" id="menu-options">
					<div id="option-selected" />
					<div id="option-icon" title="Search">
						<FaListUl color="#0d0d0d" size={40} />
					</div>
				</Link>
				<Link to="/register" id="menu-options" title="Register">
					<div id="option-selected" />
					<div id="option-icon">
						<FaPlus color="#0d0d0d" size={40} />
					</div>
				</Link>
			</div>
		</aside>
	);
};

export default Menu;
