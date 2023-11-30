import Modal from "../../Modal";
import DialogCloseButton from "../../components/DialogCloseButton";
import SaveButton from "../../components/SaveButton";
import { useAppDispatch, useAppSelector } from "../../../../slices/hooks";
import Selector from "./components/Selector";
import { addNewProductToWaybill, setAddProductToWaybillDialogOpen, setNewProductRow, waybillProduct } from "@/slices/waybillsSlice";
import { getAllProducts, productRow } from "@/slices/productSlice";
import Input from "./components/Input";

export default function ProductAddToWaybillModal() {

    const dispatch = useAppDispatch()

    const data = useAppSelector((state) => state.waybills.newProductRow) as waybillProduct;

    const products = useAppSelector((state) => state.products.list) as productRow[];

    if(products.length === 0){
        dispatch(getAllProducts())
    }

    const dialogOpen = useAppSelector((state) => state.waybills.addProductToWaybillDialogOpen) as boolean;


    return (
        <Modal open={dialogOpen} onClose={() => dispatch(setAddProductToWaybillDialogOpen(false))}>
            <div class="relative p-4 w-full max-w-md max-h-full">
                <div class="relative bg-white rounded-lg shadow dark:bg-gray-700">
                    <div class="flex items-center justify-between p-4 md:p-5 border-b rounded-t dark:border-gray-600">
                        <h3 class="text-lg font-semibold text-gray-900 dark:text-white">
                            Add Product
                        </h3>
                        <DialogCloseButton onClick={() => dispatch(setAddProductToWaybillDialogOpen(false))} />
                    </div>
                    <div class="p-4 md:p-5 md:w-96 w-20">
                        <div class="grid gap-5 mb-4 grid-cols-2">
                            <Selector onChange={(e: string) => dispatch(setNewProductRow({ ...data, product: products.filter(product => product.name === e)[0] }))}
                                value={data.product.name}
                                values={products.map(product => product.name)}
                                placeHolder="Product"
                            />
                            <Input value={data.count.toString()} onChange={(e: string) => dispatch(setNewProductRow({ ...data, count: Number(e) }))} placeHolder="Type count" label="Count" type="number"/>
                            <Input value={data.price.toString()} onChange={(e: string) => dispatch(setNewProductRow({ ...data, price: Number(e) }))} placeHolder="Type price" label="Price" type="number"/>
                        </div>
                        <div class="flex space-x-5">
                            <SaveButton onClick={() => dispatch(addNewProductToWaybill(data))} />
                        </div>
                    </div>
                </div>
            </div>
        </Modal>
    )
}