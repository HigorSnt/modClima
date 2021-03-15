import React, { useEffect, useState } from 'react';
import { FaRegCalendarPlus, FaRegCalendarTimes } from 'react-icons/fa';
import { GiWindmill } from 'react-icons/gi';
import { useParams } from 'react-router';
import Skeleton from 'react-loading-skeleton';
import { Link } from 'react-router-dom';

import Menu from '../../components/Menu';
import Empty from '../../components/Empty';
import Card from '../../components/Card';

import { Mills, Param } from '../../@types';

import api from '../../services/api';
import { formatDate } from '../../utils/date';

import './styles.css';

const MillDetails: React.FC = () => {
	const params = useParams<Param>();

	const [mill, setMill] = useState<Mills>();

	useEffect(() => {
		api.get(`mills/${params.id}`).then(({ data }) => {
			setMill(data);
		});
	}, [params.id]);

	if (!mill) {
		return (
			<div id="mill-details-box">
				<Menu />
				<div id="mill-info-container">
					<div id="mill-title">
						<Skeleton width={300} height={60} />
					</div>
					<div id="mill-harvests">
						<Skeleton width={300} height={160} count={4} style={{ margin: 20 }} />
					</div>
				</div>
			</div>
		);
	}

	return (
		<div id="mill-details-box">
			<Menu />
			<div id="mill-info-container">
				<div id="mill-title">
					<GiWindmill size={60} color="#05aff2" />
					<span>{mill?.name}</span>
				</div>

				<div id="mill-harvests" className="scrollable-area">
					{mill?.harvests?.length === 0 && <Empty />}
					{mill?.harvests?.map(harvest => (
						<Card key={harvest.id}>
							<Link to={`/harvests/${harvest.id}`} className="item-link">
								<div className="mill-harvests-items">
									<div className="mill-title-box">
										<span className="mill-title">Code</span>
										<p className="mill-title">
											<strong>{harvest.code}</strong>
										</p>
									</div>
									<div className="mill-info-area">
										<FaRegCalendarPlus size={25} color="#383D3B" />
										<span>{`This harvest starts in ${formatDate(harvest.start)}`}</span>
									</div>
									<div className="mill-info-area">
										<FaRegCalendarTimes size={25} color="#383D3B" />
										<span>{`This harvest ends in ${formatDate(harvest.end)}`}</span>
									</div>
								</div>
							</Link>
						</Card>
					))}
				</div>
			</div>
		</div>
	);
};

export default MillDetails;
