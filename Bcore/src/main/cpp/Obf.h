#pragma once

#include <stddef.h>

#ifdef __cplusplus
extern "C" {
#endif
const char *obf_get(int id);
const char *obf_maps(void);
const char *obf_linker_basename(void);
const char *obf_linker_pathname(void);
const char *obf_sym_mutex(void);
const char *obf_sym_dlopen_ext(void);
const char *obf_sym_do_dlopen(void);
const char *obf_sym_dlopen_o(void);
const char *obf_sym_loader_dlopen(void);
#ifdef __cplusplus
}
#endif

#ifdef __cplusplus
struct ObfRef {
    const char *p;
    const char *c_str() const { return p; }
};
#define OBF(id) ObfRef{obf_get(id)}
#define OBF_C(id) obf_get(id)
#endif
