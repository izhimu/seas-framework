import Qs from "qs";
import { Page, Result, api } from "@izhimu/seas-core";
import { ${className} } from "../entity/${fileName}";

const url = "${pathName}";

export const page = (
  // eslint-disable-next-line no-shadow
  page: Page<${className}>,
  search: ${className},
): Promise<Result<Page<${className}>>> => {
  return api().get(`${r'${url}'}/page`, {
    params: { ...page, ...search },
    paramsSerializer: {
      serialize(params) {
        return Qs.stringify(params, {
          arrayFormat: "indices",
          allowDots: true,
        });
      },
    },
  });
};

export const get = (id: string): Promise<Result<${className}>> => {
  return api().get(`${r'${url}'}/${r'${id}'}`);
};

export const save = (data: ${className}): Promise<Result<void>> => {
  return api().post(`${r'${url}'}`, data);
};

export const update = (data: ${className}): Promise<Result<void>> => {
  return api().put(`${r'${url}'}`, data);
};

export const del = (id: string): Promise<Result<void>> => {
  return api().delete(`${r'${url}'}/${r'${id}'}`);
};
