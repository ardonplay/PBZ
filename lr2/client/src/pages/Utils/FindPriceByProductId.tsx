import { DatePicker } from "@/components/DatePicker";
import Input from "@/components/Modal/impl/Waybill/components/Input";
import TableHead from "@/components/Table/TableHead";
import TableRow from "@/components/Table/TableRow";
import { getAllPriceById, priceById, setDateEnd, setDateStart, setProductId } from "@/slices/findPriceSlice";
import { useAppDispatch, useAppSelector } from "@/slices/hooks";

export default function FindPriceByProductId() {
    const dispatch = useAppDispatch();



    const productPerDate = useAppSelector((state) => state.priceById.list) as priceById[]
    const date_end = useAppSelector(state => state.priceById.date_end)
    const date_start = useAppSelector(state => state.priceById.date_start)
    const product_id = useAppSelector(state => state.priceById.id)

    return (
        <>
            <div class="flex space-x-2 mb-4">
                <DatePicker value={date_start} onChange={(e: string) => { dispatch(setDateStart(e)) }} />
                <DatePicker value={date_end} onChange={(e: string) => { dispatch(setDateEnd(e)) }} />
                <Input value={product_id.toString()} onChange={(e: string) => { dispatch(setProductId(e)) }} placeHolder="Type id" type="number" />
                <button onClick={() => dispatch(getAllPriceById({id: product_id, date_start: date_start, date_end: date_end}))}
                    class="text-white items-center self-end bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                    find
                </button>
            </div>

            <div class="w-full h-full flex flex-col">
                <div class="overflow-x-auto shadow-md sm:rounded-lg">
                    <table class="w-full text-sm text-left rtl:text-right text-gray-500 bg-slate-800">
                        <TableHead columns={["Name", "Date", "Price"]} />
                        <tbody>
                            {productPerDate.map((product, i) => <TableRow key={i} id={i} data={[product.name, product.date, product.price]} />)}
                        </tbody>
                    </table>
                </div>

            </div>

        </>
    );
}