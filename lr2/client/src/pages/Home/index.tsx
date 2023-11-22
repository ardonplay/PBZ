import Table from '../../components/Table';
import Dashboard from '../../components/Dashboard/index';
import './style.css';

export function Home() {
	return (
		<div class="home flex flex-row justify-start ">
			<Dashboard />
			<div class="p-4 flex flex-col w-full h-screen bg-slate-900">
				<span class="text-2xl pb-4 text-center font-bold">Products</span>
				<Table />

			</div>
		</div>
	);
}
