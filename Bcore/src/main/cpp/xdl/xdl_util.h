






















#ifndef IO_GITHUB_HEXHACKING_XDL_UTIL
#define IO_GITHUB_HEXHACKING_XDL_UTIL

#include <errno.h>
#include <stdbool.h>
#include <stddef.h>

const char *obf_linker_basename(void);
const char *obf_linker_pathname(void);
#define XDL_UTIL_LINKER_BASENAME obf_linker_basename()
#define XDL_UTIL_LINKER_PATHNAME obf_linker_pathname()
#ifndef __LP64__
#define XDL_UTIL_APP_PROCESS_BASENAME   "app_process32"
#define XDL_UTIL_APP_PROCESS_PATHNAME   "/system/bin/app_process32"
#define XDL_UTIL_APP_PROCESS_BASENAME_K "app_process"
#define XDL_UTIL_APP_PROCESS_PATHNAME_K "/system/bin/app_process"
#else
#define XDL_UTIL_APP_PROCESS_BASENAME "app_process64"
#define XDL_UTIL_APP_PROCESS_PATHNAME "/system/bin/app_process64"
#endif
#define XDL_UTIL_VDSO_BASENAME "[vdso]"

#define XDL_UTIL_TEMP_FAILURE_RETRY(exp)   \
  ({                                       \
    __typeof__(exp) _rc;                   \
    do {                                   \
      errno = 0;                           \
      _rc = (exp);                         \
    } while (_rc == -1 && errno == EINTR); \
    _rc;                                   \
  })

#ifdef __cplusplus
extern "C" {
#endif

const char *obf_maps(void);
bool xdl_util_starts_with(const char *str, const char *start);
bool xdl_util_ends_with(const char *str, const char *ending);

size_t xdl_util_trim_ending(char *start);

int xdl_util_get_api_level(void);

#ifdef __cplusplus
}
#endif

#endif
