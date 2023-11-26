import Modal from "../../Modal";
import { setSelectedRow, productRow, setAddNewDialogOpen,addNewProduct } from '../../../../slices/productSlice';
import { useSelector, useDispatch } from 'react-redux';
import ProductCategories from "./components/ProductCategories";
import ProductNameInput from "./components/ProductNameInput";
import DialogCloseButton from "../../components/DialogCloseButton";
import SaveButton from "../../components/SaveButton";
import { ThunkDispatch } from "@reduxjs/toolkit";

export default function ProductAddModal() {

    const dispatch = useDispatch();
    const asyncdispatch = useDispatch<ThunkDispatch<any, any, any>>()
    
    const data = useSelector((state) => state.products.selectedRow) as productRow;

    const productTypes = useSelector((state) => state.products.types) as string[]

    const dialogOpen = useSelector((state) => state.products.addNewDialogOpen) as boolean;

    const setData = (data: productRow) => {
        dispatch(setSelectedRow(data))
    }

    return (
        <Modal open={dialogOpen} onClose={() => dispatch(setAddNewDialogOpen(false))}>
            <div class="relative p-4 w-full max-w-md max-h-full">
                <div class="relative bg-white rounded-lg shadow dark:bg-gray-700">
                    <div class="flex items-center justify-between p-4 md:p-5 border-b rounded-t dark:border-gray-600">
                        <h3 class="text-lg font-semibold text-gray-900 dark:text-white">
                            Add Product
                        </h3>
                        <DialogCloseButton onClick={() => dispatch(setAddNewDialogOpen(false))} />
                    </div>
                    <div class="p-4 md:p-5 md:w-96 w-20">
                        <div class="grid gap-5 mb-4 grid-cols-2">
                            <ProductNameInput onChange={(e: string) => setData({ ...data, name: e })} value={data.name} />
                            <ProductCategories onChange={(e: string) => setData({ ...data, type: e })} value={data.type} values={productTypes} />
                        </div>
                        <div class="flex space-x-5">
                            <SaveButton onClick={() => dispatch(addNewProduct(data))} />
                        </div>

                    </div>
                </div>
            </div>
        </Modal>
    )
}