import { DatePicker } from "@/components/DatePicker";
import Input from "@/components/Modal/impl/Waybill/components/Input";
import TableHead from "@/components/Table/TableHead";
import TableRow from "@/components/Table/TableRow";
import { findWaybillsByProductId ,setProductId } from "@/slices/findByProductSlice";
import { useAppDispatch, useAppSelector } from "@/slices/hooks";

export default function FindWaybillsByProductId() {
    const dispatch = useAppDispatch();

    const productID = useAppSelector(state => state.priceByProduct.product_id);
    
    const waybills = useAppSelector(state => state.priceByProduct.list)
    return (
        <>  
          <div class="flex space-x-2 mb-4">
                <Input value={productID.toString()} onChange={(e: string) => { dispatch(setProductId(e)) }} placeHolder="Type id" type="number" />
                <button onClick={() => dispatch(findWaybillsByProductId(productID))}
                    class="text-white items-center self-end bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                    find
                </button>
            </div>

            <span class="text-2xl pb-4 text-center font-bold dark:text-white text-gray-900">Waybills</span>
            <div class="w-full h-full flex flex-col">
                <div class="overflow-x-auto shadow-md sm:rounded-lg">
                    <table class="w-full text-sm text-left rtl:text-right text-gray-500 bg-slate-800">
                        <TableHead columns={["id", "Customer", "Date", "Destination"]} />
                        <tbody>
                            {waybills.map((waybill, i) => <TableRow key={i} id={i} data={[waybill.id.toString(), waybill.customer_id, waybill.date, waybill.destination.name]}/>)}
                        </tbody>
                    </table>
                </div>
            </div>


        </>
    );
}