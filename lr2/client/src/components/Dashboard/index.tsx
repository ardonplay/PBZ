import ProductsLogo from "../../assets/productsLogo";
import CustomersLogo from "../../assets/customersLogo";
import Logo from "../../assets/logo";
import WaybillsLogo from "../../assets/waybillsLogo";
import DestinationsLogo from "../../assets/destinationsLogo";
import DashboardComponent from "./DashboardComponent";
import UtilsLogo from "@/assets/utilsLogo";

export default function Dashboard() {
    return (
        <div class="flex flex-col w-64 h-screen transition-transform -translate-x-full sm:translate-x-0 px-3 py-4 overflow-y-auto bg-gray-50 dark:bg-gray-800">
            <div class="flex">
                <Logo />
                <span class="flex-1 ms-3 whitespace-nowrap font-bold text-gray-900 dark:text-white">aboba client</span>
            </div>
            <ul class="mt-5 space-y-2 font-medium w-full">
                <DashboardComponent logo={CustomersLogo()} title="Customers" link="/customers"/>
                <DashboardComponent logo={ProductsLogo()} title="Products" link="/products"/>
                <DashboardComponent logo={WaybillsLogo()} title="Waybills" link="/waybills"/>
                <DashboardComponent logo={DestinationsLogo()} title="Destinations" link="/destinations"/>
                <DashboardComponent logo={UtilsLogo()} title="Utils" link="/utils"/>
            </ul>
            <div class="flex mt-auto text-white">
                2023 V. Moshchuk
            </div>
        </div>)
}