import ProductsLogo from "../assets/productsLogo";
import CustomersLogo from "../assets/customersLogo";
import Logo from "../assets/logo";
import WaybillsLogo from "../assets/waybillsLogo";
import DestinationsLogo from "../assets/destinationsLogo";
import DashboardComponent from "./DashboardComponent";

export default function Dashboard() {
    return (
        <div class="flex flex-col w-64 h-screen transition-transform -translate-x-full sm:translate-x-0 px-3 py-4 overflow-y-auto bg-gray-50 dark:bg-gray-800">
            <div class="flex">
                <Logo />
                <span class="flex-1 ms-3 whitespace-nowrap font-bold text-white">aboba client</span>
            </div>
            <ul class="mt-5 space-y-2 font-medium w-full">
                <DashboardComponent logo={CustomersLogo()} title="Customers" />
                <DashboardComponent logo={ProductsLogo()} title="Products" />
                <DashboardComponent logo={WaybillsLogo()} title="Waybills" />
                <DashboardComponent logo={DestinationsLogo()} title="Destinations"/>
            </ul>
            <div class="flex mt-auto text-white">
                2023 V. Moshchuk
            </div>
        </div>)
}