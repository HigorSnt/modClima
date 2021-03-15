import React from 'react';

import emptyIcon from '../../images/page-blank.png';

import './styles.css';

const Empty: React.FC = () => {
	return (
		<div className="empty-box">
			<p>No items registered yet!</p>
			<img src={emptyIcon} alt="empty" />
		</div>
	);
};

export default Empty;
