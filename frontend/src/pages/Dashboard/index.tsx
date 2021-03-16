import React from 'react';
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';
import { useHistory } from 'react-router';
import { toast } from 'react-toastify';

import List from '../List';
import Menu from '../../components/Menu';

import { Notification } from '../../@types';

const Dashboard: React.FC = () => {
	const socket = new SockJS('https://modclima.herokuapp.com//ws');
	const stompClient = Stomp.over(socket);
	const history = useHistory();

	stompClient.connect({}, frame => {
		console.log('connected ' + frame);
		stompClient.subscribe('/topic/save', message => {
			const notification: Notification = JSON.parse(message.body);

			toast.success('🎉 ' + notification.message, {
				position: 'top-right',
				onClick: () => {
					toast.clearWaitingQueue();
					history.push(notification.redirectURL);
				},
				onOpen: () => toast.clearWaitingQueue(),
				autoClose: 5000,
				hideProgressBar: false,
				pauseOnHover: false,
				draggable: false,
			});
		});
	});

	return (
		<div className="container">
			<Menu />
			<List />
		</div>
	);
};

export default Dashboard;
