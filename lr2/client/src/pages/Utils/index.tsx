
import { Route, Router } from "preact-iso";
import { Link, useRouter } from "preact-router";
import MaxWaybillsPerDate from "./MaxWaybillsPerDate";
import FindPriceByProductId from "./FindPriceByProductId";

export default function UtilsPage(){
    return(
        <div>
        
            <Link href="/utils/max_waybills_per_date" class="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700 group">
                <span class="flex-1 ms-3 whitespace-nowrap">Max waybills per date</span>
            </Link>
            <Link href="/utils/find_price_by_product_id" class="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700 group">
                <span class="flex-1 ms-3 whitespace-nowrap">Find price by product id</span>
            </Link>
        </div>
    )
}