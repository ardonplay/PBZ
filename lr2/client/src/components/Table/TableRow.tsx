interface RowProps { data: string[], checkbox?: boolean, edit?: boolean, id?: number }
export default function TableRow({ data = [], checkbox = true, edit = true, id=0 }: RowProps) {
    return (
        <tr class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">
            {checkbox ? <td class="w-4 p-4">
                <div class="flex items-center">
                    <input id={`checkbox-table-search-${id}`} type="checkbox" class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 dark:focus:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600" />
                    <label for={`checkbox-table-search-${id}`} class="sr-only">checkbox</label>
                </div>
            </td> : null}
            {data.map(col => <td class="px-6 py-4">
                {col}
            </td>)}
            {edit ? <td class="px-6 py-4">
                <a href="#" class="font-medium text-blue-600 dark:text-blue-500 hover:underline">Edit</a>
            </td> : null}

        </tr>
    )
}