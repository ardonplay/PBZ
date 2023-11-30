import { useEffect } from "react";
import TableHead from "@/components/Table/TableHead";
import TableRow from "@/components/Table/TableRow";
import { useAppDispatch, useAppSelector } from "@/slices/hooks";
import {setEditDialogOpen, getAllWaybills, setAddNewDialogOpen,  waybillRow, getFullWaybill } from "@/slices/waybillsSlice";
import WaybillEditModal from "@/components/Modal/impl/Waybill/WaybillEditModal";
import WaybillAddModal from "@/components/Modal/impl/Waybill/WaybillAddModal";
import ProductAddToWaybillModal from "@/components/Modal/impl/Waybill/ProductAddToWaybillModal";


export default function Waybills() {

    const dispatch = useAppDispatch();
    
    useEffect(() => {
       dispatch(getAllWaybills())
    }, [])

    const waybills = useAppSelector((state) => state.waybills.list) as waybillRow[]

   
    const openDialog = (id: number) => {
        dispatch(getFullWaybill(waybills[id].id))
        dispatch(setEditDialogOpen(true))
    }

    return (
        <>
            <span class="text-2xl pb-4 text-center font-bold dark:text-white text-gray-900">Waybills</span>
            <div class="w-full h-full flex flex-col">
                <div class="overflow-x-auto shadow-md sm:rounded-lg">
                    <table class="w-full text-sm text-left rtl:text-right text-gray-500 bg-slate-800">
                        <TableHead columns={["id", "Customer", "Date","Destination", "Action"]} />
                        <tbody>
                            {console.log(waybills)}
                            {waybills.map((waybill, i) => <TableRow key={i} id={i} data={[waybill.id.toString(), waybill.customer_id, waybill.date, waybill.destination.name]} action={ <button class="font-medium text-blue-600 dark:text-blue-500 hover:underline" onClick={() => openDialog(i)}>Edit</button>} />)}
                        </tbody>
                    </table>
                </div>

                <nav class="flex items-center flex-column flex-wrap md:flex-row justify-between mt-auto" aria-label="Table navigation">
                    <span class="text-sm font-normal text-gray-500 dark:text-gray-400 mb-4 md:mb-0 block w-full md:inline md:w-auto">Showing <span class="font-semibold text-gray-900 dark:text-white">1-10</span> of <span class="font-semibold text-gray-900 dark:text-white">1000</span></span>

                    <button type="button" onClick={() => dispatch(setAddNewDialogOpen(true))} class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800">Add</button>

                    <ul class="inline-flex -space-x-px rtl:space-x-reverse text-sm h-8">
                        <li>
                            <a href="#" class="flex items-center justify-center px-3 h-8 ms-0 leading-tight text-gray-500 bg-white border border-gray-300 rounded-s-lg hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white">Previous</a>
                        </li>
                        <li>
                            <a href="#" class="flex items-center justify-center px-3 h-8 leading-tight text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white">1</a>
                        </li>
                        <li>
                            <a href="#" class="flex items-center justify-center px-3 h-8 leading-tight text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white">2</a>
                        </li>
                        <li>
                            <a href="#" aria-current="page" class="flex items-center justify-center px-3 h-8 text-blue-600 border border-gray-300 bg-blue-50 hover:bg-blue-100 hover:text-blue-700 dark:border-gray-700 dark:bg-gray-700 dark:text-white">3</a>
                        </li>
                        <li>
                            <a href="#" class="flex items-center justify-center px-3 h-8 leading-tight text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white">4</a>
                        </li>
                        <li>
                            <a href="#" class="flex items-center justify-center px-3 h-8 leading-tight text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white">5</a>
                        </li>
                        <li>
                            <a href="#" class="flex items-center justify-center px-3 h-8 leading-tight text-gray-500 bg-white border border-gray-300 rounded-e-lg hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white">Next</a>
                        </li>
                    </ul>
                </nav>
            </div>

            <WaybillEditModal/>

            <WaybillAddModal />

            <ProductAddToWaybillModal/>
        </>
    )
}