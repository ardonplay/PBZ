export default function Input(props: { value: string, onChange: Function, placeHolder: string, label: string, type?: string }) {
    return (
        <div class="col-span-4">
            <label class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">{props.label}</label>
            <input type={props.type ? props.type : "text"} name="name" value={props.value}
                onChange={(e) => props.onChange(e.currentTarget.value)}
                class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder={props.placeHolder} required />
        </div>
    )
}