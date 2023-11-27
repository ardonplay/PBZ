export default function ProductCategories(props: { onChange: Function, value: string, values: string[] }) {
    return (
        <div class="col-span-4">
            <label class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Category</label>
            <select
                onChange={(e) => props.onChange((e.currentTarget.value))}
                value={props.value}
                class="bg-gray-50 border border-gray-300 text-gray-900 text-sm m rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500">
                {props.values.map(value => <option value={value}>{value}</option>)}
            </select>
        </div>
    )
}