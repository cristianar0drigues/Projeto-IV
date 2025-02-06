// import '../App.css';
import estilo from './AddProjeto.module.css';
import funcao from './Tela.module.css';


export const AddProjeto = () => {
    function abrir() {
        var element = document.getElementById("telaCriada");
        var button = document.getElementById("criar");
        element.classList.toggle(funcao.desabilitado);
        button.classList.toggle(estilo.desabilitado);
    }
    return (
        <div className={estilo.add_projeto}>
            <div id="criar" className={estilo.botao} onClick={abrir}>
                <svg width="128" height="128" viewBox="0 0 128 128" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <g filter="url(#filter0_d_24_175)">
                        <rect x="4" width="120" height="120" rx="60" fill="#785EAD" />
                        <path d="M44.0002 85.7143C42.4287 85.7143 41.083 85.1552 39.963 84.0371C38.8449 82.9171 38.2859 81.5714 38.2859 80V40C38.2859 38.4286 38.8449 37.0828 39.963 35.9628C41.083 34.8448 42.4287 34.2857 44.0002 34.2857H61.143V40H44.0002V80H84.0002V62.8571H89.7145V80C89.7145 81.5714 89.1554 82.9171 88.0373 84.0371C86.9173 85.1552 85.5716 85.7143 84.0002 85.7143H44.0002ZM75.4287 57.1428V48.5714H66.8573V42.8571H75.4287V34.2857H81.143V42.8571H89.7145V48.5714H81.143V57.1428H75.4287Z" fill="white" />
                    </g>
                    <defs>
                        <filter id="filter0_d_24_175" x="0" y="0" width="128" height="128" filterUnits="userSpaceOnUse" colorInterpolationFilters="sRGB">
                            <feFlood floodOpacity="0" result="BackgroundImageFix" />
                            <feColorMatrix in="SourceAlpha" type="matrix" values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0" result="hardAlpha" />
                            <feOffset dy="4" />
                            <feGaussianBlur stdDeviation="2" />
                            <feComposite in2="hardAlpha" operator="out" />
                            <feColorMatrix type="matrix" values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0.25 0" />
                            <feBlend mode="normal" in2="BackgroundImageFix" result="effect1_dropShadow_24_175" />
                            <feBlend mode="normal" in="SourceGraphic" in2="effect1_dropShadow_24_175" result="shape" />
                        </filter>
                    </defs>
                </svg></div>
        </div>
    )
}
