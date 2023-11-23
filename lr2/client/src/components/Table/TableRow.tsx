import { useState } from "preact/hooks"
import ProductEditModal from "../Modal/ProductEditModal"
import { JSX } from "preact/jsx-runtime"

export default function TableRow(props: { data: string[], onEdit: Function, id: number, editModal: JSX.Element }) {
    

    return (
        <>
            <tr class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">
                {props.data.map(col => <td class="px-6 py-4">
                    {col}
                </td>)}
               <td class="px-6 py-4">
                    <button class="font-medium text-blue-600 dark:text-blue-500 hover:underline" onClick={() => props.onEdit(true)}>Edit</button>
                </td>
            </tr>
            {props.editModal}
        </>

    )
}