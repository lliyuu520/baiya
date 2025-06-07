export interface RuleDetail {
    id: number
    sourceField: string
    weight: number
    indexBegin: number
    indexEnd: number
    encodeType: string
    constant: string
}

export interface SourceFieldOption {
    code: string
    name: string
}

export interface EncodeTypeOption {
    code: string
    name: string
}

export const encodeTypeList: EncodeTypeOption[] = [
    { code: 'BASE_10', name: 'Base10' },
    { code: 'BASE_36', name: 'Base36' },
    { code: 'BASE_62', name: 'Base62' }
]

export const boxCodeSourceFieldList: SourceFieldOption[] = [
    { code: 'LIMITED_USE_DATE', name: '限用日期' },
    { code: 'DEPART_CODE', name: '部门编码' },
    { code: 'WORKSHOP_CODE', name: '车间编码' },
    { code: 'ORDER_CODE', name: '订单编码' },
    { code: 'PRODUCT_CODE', name: '产品编码' },
    { code: 'BOX_NO', name: '箱号' },
    { code: 'CONSTANT', name: '常量' }

]

export const bagCodeSourceFieldList: SourceFieldOption[] = [
    { code: 'LIMITED_USE_DATE', name: '限用日期' },
    { code: 'DEPART_CODE', name: '部门编码' },
    { code: 'WORKSHOP_CODE', name: '车间编码' },
    { code: 'ORDER_CODE', name: '订单编码' },
    { code: 'PRODUCT_CODE', name: '产品编码' },
    { code: 'BOX_NO', name: '箱号' },

]

export const universalCodeSourceFieldList: SourceFieldOption[] = [
    { code: 'DEPART_CODE', name: '部门编码' },
    { code: 'WORKSHOP_CODE', name: '车间编码' },
    { code: 'ORDER_CODE', name: '订单编码' },
    { code: 'PRODUCT_CODE', name: '产品编码' },
    { code: 'LIMITED_USE_DATE', name: '限用日期' }
]

