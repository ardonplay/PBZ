import { JSX } from "preact/compat";

export default function DashboardComponent(props: { logo: JSX.Element, title: string }) {
    return (
        <li>
            <a href="#" class="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700 group">
                {props.logo}
                <span class="flex-1 ms-3 whitespace-nowrap">{props.title}</span>
            </a>
        </li>
    )
}