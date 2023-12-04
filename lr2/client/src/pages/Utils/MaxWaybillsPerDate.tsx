import { useEffect } from "react";
import ProductAddModal from "../../components/Modal/impl/Product/ProductAddModal";
import ProductEditModal from "../../components/Modal/impl/Product/ProductEditModal";
import TableHead from "../../components/Table/TableHead";
import TableRow from "../../components/Table/TableRow";
import { useAppDispatch, useAppSelector } from "../../slices/hooks";
import {  getAllMaxWaybills, maxWaybillRow } from "@/slices/maxWaybillSlice";
export default function MaxWaybillsPerDate(){
    const dispatch = useAppDispatch();
    

    useEffect(() => {
        dispatch(getAllMaxWaybills())
    }, [])

    const productPerDate = useAppSelector((state) => state.maxWaybill.list) as maxWaybillRow[]


    return (
        <>
            <span class="text-2xl pb-4 text-center font-bold dark:text-white text-gray-900">Max Waybill Per Date</span>
            <div class="w-full h-full flex flex-col">
                <div class="overflow-x-auto shadow-md sm:rounded-lg">
                    <table class="w-full text-sm text-left rtl:text-right text-gray-500 bg-slate-800">
                        <TableHead columns={["id", "Name", "Date", "Price"]} />
                        <tbody>
                            {productPerDate.map((waybill, i) => <TableRow key={i} id={i} data={[waybill.id.toString(), waybill.customer_name, waybill.date, waybill.price]}/>)}
                        </tbody>
                    </table>
                </div>

        
            </div>
            
        </>
    )
}