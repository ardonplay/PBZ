import Modal from "../../Modal";
import { setSelectedRow, waybillRow, setEditDialogOpen, deleteWaybill, updateWaybill, setAddProductToWaybillDialogOpen, deleteProductFromWaybill, addNewWaybill, setAddNewDialogOpen } from '@slices/waybillsSlice';
import DialogCloseButton from "../../components/DialogCloseButton";
import SaveButton from "../../components/SaveButton";
import { useAppDispatch, useAppSelector } from "@slices/hooks"
import TableHead from "@/components/Table/TableHead";
import TableRow from "@/components/Table/TableRow";
import Selector from "./components/Selector";
import { DatePicker } from "@/components/DatePicker";
import { customerRow, getAllCustomers } from "@/slices/customerSlice";
import { destinationRow, getAllDestinations } from "@/slices/destinationSlice";

export default function WaybillAddModal() {

    const dispatch = useAppDispatch();

    const data = useAppSelector((state) => state.waybills.selectedRow) as waybillRow;

    const dialogOpen = useAppSelector((state) => state.waybills.addNewDialogOpen) as boolean;

    const customers = useAppSelector(state => state.customers.list) as customerRow[];

    const destinations = useAppSelector(state => state.destinations.list) as destinationRow[]

    if(customers.length === 0){
        dispatch(getAllCustomers())
    }

    if(destinations.length === 0){
        dispatch(getAllDestinations())
    }

    return (
        <Modal open={dialogOpen} onClose={() => dispatch(setAddNewDialogOpen(false))}>
            <div class="relative p-4 w-full max-h-full">
                <div class="relative bg-white rounded-lg shadow dark:bg-gray-700">
                    <div class="flex items-center justify-between p-4 md:p-5 border-b rounded-t dark:border-gray-600">
                        <h3 class="text-lg font-semibold text-gray-900 dark:text-white">
                            Edit Waybill
                        </h3>
                        <DialogCloseButton onClick={() => dispatch(setAddNewDialogOpen(false))} />
                    </div>
                    <div class="p-4">

                        <div class="overflow-x-auto  shadow-md sm:rounded-lg p-3">
                            <span class="text-2xl pb-4 text-center font-bold dark:text-white text-gray-900">Products</span>
                            <div class="mb-4">
                                <table class="w-full text-sm text-left rtl:text-right text-gray-500 bg-slate-800">
                                    <TableHead columns={["id", "Name", "Type", "Count", "Price", "Action"]} />
                                    <tbody>
                                        {data.waybill_products.map((product, i) => <TableRow key={i} id={i} data={[product.product.id.toString(), product.product.name, product.product.type.name, product.count.toString(), product.price.toString()]} action={<button class="font-medium text-blue-600 dark:text-blue-500 hover:underline" onClick={() => {dispatch(deleteProductFromWaybill(product))}}>Delete</button>} />)}
                                    </tbody>
                                </table>
                            </div>
                            <button onClick={() => dispatch(setAddProductToWaybillDialogOpen(true))}
                                class="text-white inline-flex items-center bg-blue-800 hover:bg-blue-900 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-800 dark:hover:bg-blue-00 dark:focus:ring-blue-800">
                                Add row
                            </button>   
                        </div>


                        <div class="w-full py-2">
                            <span class="text-2xl pb-4 text-center font-bold dark:text-white text-gray-900">Customer</span>
                            <Selector onChange={(e: string) => {dispatch(setSelectedRow({...data, customer_id: customers.filter(customer => customer.name === e)[0].id}))}} value={customers.filter(customer => customer.id === data.customer_id).map(customer => customer.name)[0]} values={customers.map(customer => customer.name)} placeHolder="" />
                        </div>

                        <div class="w-full py-2">
                            <span class="text-2xl pb-4 text-center font-bold dark:text-white text-gray-900">Destination</span>
                            <Selector onChange={(e: string) => { dispatch(setSelectedRow({...data, destination: destinations.filter(destination => destination.name === e)[0]}))}} value={data.destination.name} values={destinations.map(destination => destination.name)} placeHolder="" />
                        </div>

                        <div class="w-full py-2">
                            <span class="text-2xl pb-4 text-center font-bold dark:text-white text-gray-900">Date</span>
                            <DatePicker value={data.date} onChange={(e: string) => dispatch(setSelectedRow({...data, date: e})) }/>
                        </div>




                        {/* <ProductNameInput onChange={(e: string) => setData({ ...data, name: e })} value={data.name} />
                            <ProductCategories onChange={(e: string) => setData({ ...data, type: productTypes.filter(type => type.name === e)[0] })} value={data.type.name} values={productTypes.map(type => type.name)} /> */}
                        <div class="flex space-x-5 mt-4">
                            <SaveButton onClick={() => dispatch(addNewWaybill(data))} />
                        </div>

                    </div>
                </div>
            </div>
        </Modal>
    )
}