
import axios from "axios";

const apiService = axios.create()({
    baseURL: "localhost:5432/fastannotation",
});