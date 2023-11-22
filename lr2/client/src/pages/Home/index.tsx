import Dashboard from '../../components/Dashboard';
import './style.css';

export function Home() {
	return (
		<div class="home flex flex-row justify-start ">
			<Dashboard />
			<div class="flex w-full h-screen  bg-red-500">
					ABOBA
			</div>
		</div>
	);
}
