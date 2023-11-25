import { useEffect } from "react";
import ProductAddModal from "../../components/Modal/impl/Product/ProductAddModal";
import ProductEditModal from "../../components/Modal/impl/Product/ProductEditModal";
import TableHead from "../../components/Table/TableHead";
import TableRow from "../../components/Table/TableRow";
import { setSelectedRow, productRow, setEditDialogOpen, setAddNewDialogOpen, setProducts } from '../../slices/productSlice';
import { useSelector, useDispatch } from 'react-redux';
import axios from "axios";
export default function Products() {
    const dispatch = useDispatch();


    useEffect(() => {
        axios.get("http://localhost:8080/api/v1/products").then(responce => dispatch(setProducts(responce.data as productRow[])))
    }, [])

    const products = useSelector((state) => state.products.list) as productRow[]

   
    const openDialog = (id: number) => {
        console.log(id)
        dispatch(setSelectedRow(products[id]))
        dispatch(setEditDialogOpen(true))
    }

    return (
        <>
            <span class="text-2xl pb-4 text-center font-bold dark:text-white text-gray-900">Products</span>
            <div class="w-full h-full flex flex-col">
                <div class="overflow-x-auto shadow-md sm:rounded-lg">
                    <table class="w-full text-sm text-left rtl:text-right text-gray-500 bg-slate-800">
                        <TableHead columns={["id", "Name", "Type", "Action"]} />
                        <tbody>
                            {products.map((product, i) => <TableRow key={i} id={i} data={[product.id.toString(), product.name, product.type]} onEdit={openDialog} />)}
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

            <ProductEditModal />

            <ProductAddModal />
        </>
    )
}