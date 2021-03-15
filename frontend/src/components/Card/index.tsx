import React from 'react';

import './styles.css';

interface CardProps {
	children: JSX.Element;
}

const Card: React.FC<CardProps> = ({ children }: CardProps) => {
	return <div className="card">{children}</div>;
};

export default Card;
